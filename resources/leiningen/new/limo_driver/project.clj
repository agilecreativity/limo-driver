(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "Selenium automation with the help of Limo"
  :url "http://github.com/<your-github>/{{name}}"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [limo "0.1.8"]{{{project-clj-deps}}}]
  :main {{name}}.core)
