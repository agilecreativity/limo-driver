(ns {{name}}.example.core-login
    (:require [limo.api :as api]
              [limo.driver :as driver]))

(defn starter-page
  "Navigate to the starter page"
  [url]
  (api/set-driver! (driver/create-chrome))
  (api/implicit-wait 1000)
  (api/to url))

(defn login-github
  "Quick login to Github"
  [url username password]
  (starter-page url)
  (let [username-selector "#login_field"
        password-selector "#password"]
    (api/fill-form {username-selector username
                    password-selector password}))
  (api/click-when-visible "#login > form > div.auth-form-body.mt-3 > input.btn.btn-primary.btn-block"))

(defn login-gmail
  "Quick login to Gmail"
  [url username password]
  (let [username-selector "#identifierId"
        password-selector "#password > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(1)"]
    (starter-page url)

    (api/fill-form {username-selector username})
    (api/click-when-visible "#identifierNext > content > span")

    (api/fill-form {password-selector password})
    (api/click-when-visible "#passwordNext > content > span")))
