(ns ring-fun.core
  (:require [clojure.pprint :as pp]
            [ring.middleware.stacktrace]
            [ring-fun.middleware :as mdw]
            [ring-fun.address :as address]
            [clj-json.core :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes compojure-app
  (GET "/" []  {:status 200 :headers {"Content-Type" "text/html"} :body "<p>A GET request response</p>"})
  (GET "/addresses" [] (json-response (address/find-all)))
  (GET "/addresses/:id" [id] (json-response (address/find id)))
  (POST "/addresses" {params :params}  (json-response (address/create params)))
  (route/files "/")
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
    (wrap-spy)
    (mdw/wrap-request-logging)))

;; (require 'ring.adapter.jetty)
;; (defonce server (ring.adapter.jetty/run-jetty #'app {:port 3000 :join? false}))
