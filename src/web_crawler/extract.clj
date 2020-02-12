(ns web-crawler.extract
  (:require [clj-http.client :as client]
            [hickory.core :as html]
            [hickory.select :as select]
            [org.bovinegenius [exploding-fish :as uri]])
  (:gen-class))

(def test-uri-string "https://port-zero.com")
(def test-uri (uri/uri "https://port-zero.com"))

(defn extend-with-base
  "If a given URL is relative, extend it with the base path."
  [url base]
  (if (uri/absolute? url)
    (uri/uri url)
    (uri/resolve-path base url)))

(defn extract-links
  "Given a html document in hiccup format, extract the links."
  [site-htree base]
  (->> (select/select (select/tag :a) site-htree)
       (map #(-> % :attrs :href))
       (map #(extend-with-base % base))
       distinct))

(defn get-outgoing
  "Given a URL compute all outgoing URL links."
  [url]
  (-> (client/get (str url))
      :body
      html/parse
      html/as-hickory
      (extract-links url)))
