(ns {{name}}.example.quick-login
    (:require [limo.api :as api]
              [limo.driver :as driver]
              [easy-config.core :refer [read-config] :as ecf]
              [{{name}}.example.core-login :refer [login-github
                                                   login-gmail
                                                   read-config] :as li]))

(declare github-login)
(declare gmail-login)

(defn- service-dispatch
  "Dispatch service from the given service-type"
  [service-type config]
  (let [srv-type (name service-type)]
    (cond
      (= srv-type "github") (github-login config)
      (= srv-type "gmail")  (gmail-login config)
      :else                 (throw (str "Not supported service " srv-type)))))

(defn github-login
  "Login to Github"
  [config-file]
  (let [{:keys [url username password]} (ecf/read-config config-file)]
    (li/login-github url username password)))

(defn gmail-login
  [config-file]
  (let [{:keys [url username password]}
        (ecf/read-config config-file)]
    (li/login-gmail url username password)))

(defn -main [& args]
  (let [[service-type config] args]
    (service-dispatch service-type config)))
