(ns web-crawler.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-d" "--domain DOMAIN" "Root domain to start the crawler."
    :default "https://port-zero.com/"]])

(defn -main
  "Main entry point web-crawler."
  [& args]
  (let [options (-> (parse-opts args cli-options) :options)
        domain (:domain options)]
    (println domain)))
