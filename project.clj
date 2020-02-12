(defproject web-crawler "0.1.0-SNAPSHOT"
  :description "Simple web-crawler for one domain."
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[clj-http "3.10.0"]
                 [hickory "0.7.1"]
                 [lacij "0.10.0"]
                 [org.bovinegenius/exploding-fish "0.3.6"] 
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "0.4.2"]]
  :main ^:skip-aot web-crawler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
