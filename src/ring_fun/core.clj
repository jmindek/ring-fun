(ns ring-fun.core
  (:require [clojure.pprint :as pp]
            [ring.middleware.stacktrace]
            [ring-fun.middleware :as mdw]
            [ring-fun.address :as address]
            [ring.middleware.basic-authentication :as basic]
            [clj-json.core :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring-fun.forms :as forms]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn authenticated? [name pass]
  (and (= name "user")
    (= pass "pass")))

(defroutes compojure-app
  (GET "/" []  {:status 200 :headers {"Content-Type" "text/html"} :body "<p>A GET request response</p>"})
  (GET "/login" [] {:status 200 :body forms/login-page})
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
    (mdw/wrap-request-logging)
    (basic/wrap-basic-authentication authenticated?)))

;; (require 'ring.adapter.jetty)
;; (defonce server (ring.adapter.jetty/run-jetty #'app {:port 3000 :join? false}))
