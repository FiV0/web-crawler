(ns web-crawler.graph
  (:require [org.bovinegenius [exploding-fish :as uri]]
            [web-crawler.extract :as extract]
            [rhizome.viz :as viz])
  (:gen-class))

(defn compute-graph
  "Given a url. Compute a simple graph on internal links
  of the website."
  [url]
  (loop [seen #{url}
         queue (conj clojure.lang.PersistentQueue/EMPTY url)
         graph {}]
    (if-let [url (peek queue)]
      (let [neighs (extract/get-outgoing-internal url)
            new-neighs (remove #(seen %) neighs)]
        ;; (println url)
        (recur (reduce #(conj %1 %2) seen new-neighs)
               (reduce #(conj %1 %2) (pop queue) new-neighs)
               (assoc graph url neighs)))
      graph)))

(defn visualize-sitemap-graph
  "Given a url, print the sitemap."
  [url]
  (let [g (compute-graph url)]
    (viz/view-graph
     (keys g) g
     :node->descriptor (fn [url] {:label (str url)}))))
