(ns ring-fun.forms
  (:require [hiccup.core :as hiccup]))

(def login-page (hiccup/html [:p "Login page"]
                  [:input {:text "Username" :value nil :placeholder "Username or email"} nil]
                  [:input {:type "password" :name "password" :value nil :placeholder "Password"} nil]))


