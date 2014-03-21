(ns ring-fun.forms
  (:require [sandbar.core]
            [sandbar.forms]))

(defform user-form "/user/edit"
  :fields [(hidden :id)
           (textfield "Username" :username)]
  ;;  :load #(db/find-user %)
  :on-cancel "/"
  :on-success
  #(do
     ;;     (db/store-user %)
     ;;     (set-flash-value! :user-message "User has been saved.")
     "/")
  :validator
  #(if (< (count (:username %)) 5)
     (add-validation-error % :username "Username must have at least 5 chars.")
     %))
