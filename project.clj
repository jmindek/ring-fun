(defproject ring-fun "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring "1.2.1"]
                 [compojure "1.1.6"]]
  :ring {:handler ring-fun.core/app}
  :plugins [[lein-ring "0.8.10"]]
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]}})
