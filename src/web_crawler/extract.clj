(ns web-crawler.extract
  (:require [clj-http.client :as client]
            [hickory.core :as html]
            [hickory.select :as select]
            [org.bovinegenius [exploding-fish :as uri]])
  (:gen-class))

(defn extend-with-base
  "If a given URL is relative, extend it with the base path
  o/w leave it as it is."
  [url base]
  (if (uri/absolute? url)
    (uri/uri url)
    (uri/resolve-path base url)))

(defn unwanted-href?
  "Return true if an url is of type we are not interested in."
  [url]
  (or (clojure.string/starts-with? url "mailto:")
      (clojure.string/starts-with? url "tel:")))

(defn extract-links
  "Given a html document in hiccup format, extract the links."
  [site-htree base]
  (->> (select/select (select/tag :a) site-htree)
       (map #(-> % :attrs :href))
       (remove unwanted-href?)
       (map #(extend-with-base % base))
       distinct))

(defn get-outgoing
  "Given a URL compute all outgoing URL links."
  [url]
  (try
    (-> (client/get (str url))
        :body
        html/parse
        html/as-hickory
        (extract-links url))
    (catch Exception e
      (let [data (ex-data e)]
        (case (:status data)
          404 (println "Exception happened: 404 page not found!!!")
          (println "Unknown exception happened!!!"))
        []))))

(defn same-host?
  "Given two urls, return true if they come from the same host."
  [url1 url2]
  (= (:host url1) (:host url2)))

(defn remove-external
  "Given a list of urls remove the ones that are external with
  respect to some base-url."
  [urls base-url]
  (filter #(same-host? % base-url) urls))

(defn get-outgoing-internal
  "Same as get-outgoing, but keeps only internal links."
  [url]
  (-> (get-outgoing url)
      (remove-external url)))
