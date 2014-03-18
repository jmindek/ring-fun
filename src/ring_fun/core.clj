(ns ring-fun.core
  (:require [clojure.pprint :as pp]
            [ring.middleware.stacktrace]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(defroutes compojure-app
  (GET "/" []  {:status 200 :headers {"Content-Type" "text/html"} :body "<p>A GET request response</p>"})
  (route/not-found "<h1>Page not found</h1>"))

(defn wrap-spy [handler]
  (fn [request]
    (println "---------------")
    (println "Incoming request: ")
    (pp/pprint request)
    (let [response (handler request)]
      (println "Outgoing response: ")
      (pp/pprint response)
      (println "---------------")
      response)))


(def app
  (-> #'compojure-app
    (ring.middleware.stacktrace/wrap-stacktrace)
    (wrap-spy)))

;; (require 'ring.adapter.jetty)
;; (defonce server (ring.adapter.jetty/run-jetty #'app {:port 3000 :join? false}))
