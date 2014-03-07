(ns ring-fun.core
  (:require [clojure.pprint :as pp]
            [ring.middleware.stacktrace]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<h1>Deligate it or not maybe.</h1>"})

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

;; (defn app [request]
;;   (handler request))
(def app
  (-> #'handler
    (ring.middleware.stacktrace/wrap-stacktrace)
    (wrap-spy)))

(require 'ring.adapter.jetty)
(defonce server (ring.adapter.jetty/run-jetty #'app {:port 3000 :join? false}))
