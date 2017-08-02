(ns {{name}}.core
    (:require [limo.api :as api]
              [limo.driver :as driver]))

(defn- starter-page
  "Navigate to the starter page"
  [url]
  (api/set-driver! (driver/create-chrome))
  (api/implicit-wait 1000)
  (api/to url))

(defn -main [& args]
  ;; Note: run simple search on Duckduckgo!
  (try
    (starter-page "https://duckduckgo.com/")
    (let [search-form "#search_form_input_homepage"]
      (api/click-when-visible search-form)
      (api/send-keys search-form "Clojure")
      (api/click-when-visible "#search_button_homepage"))
    (catch Exception e
      (.printStackTrace e)
      (println (str "Expected errors: " (.getMessage e))))))
