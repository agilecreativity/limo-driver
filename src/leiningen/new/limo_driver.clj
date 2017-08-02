(ns leiningen.new.limo-driver
  (:require
   [clj-jgit.porcelain :refer [load-repo
                               git-add
                               git-init
                               git-commit] :as cjg]
   [leiningen.new.templates :refer [renderer
                                    name-to-path
                                    ->files
                                    project-name
                                    render-text
                                    sanitize-ns
                                    year
                                    slurp-resource]]
   [leiningen.core.main :as main]
   [clojure.string :as str]
   [leiningen.new.utils :refer [wrap-indent
                                dep-list
                                indent
                                indent-next]]))

;; Optional options
(def valid-options
  ["example"])

;; Build the (defn example? [opts] ..)
(doseq [opt valid-options]
  (eval
    `(defn ~(symbol (str opt "?")) [opts#]
       (some #{~(str "--" opt) ~(str "+" opt)} opts#))))

(def render (renderer "limo-driver"))

(defn project-clj-deps [opts]
  (cond-> []
    (example? opts) (conj '[easy-config "0.1.0"]
                          '[me.raynes/fs "1.4.6"])))

(defn files-to-render [opts]
  (cond->
      ["project.clj"
       ".gitignore"
       "README.md"
       "LICENSE"
       "CHANGELOG.md"
       "resources/.gitkeep"
       "src/limo_driver/core.clj"
       "test/limo_driver/core_test.clj"]
    (example? opts) (conj "src/limo_driver/example/quick_login.clj"
                          "src/limo_driver/example/core_login.clj"
                          "resources/github-config.edn"
                          "resources/gmail-config.edn")))

(defn template-data [name opts]
  {:full-name        name
   :name             (project-name name)
   :project-ns       (sanitize-ns name)
   :sanitized        (name-to-path name)
   :project-clj-deps (indent 17 (map pr-str (project-clj-deps opts))) ;; Optional project dependencies
   :year             (year)
   :example?         (example? opts)})

(defn format-files-args
  "Returns a list of pairs (vectors).
  The first element is the file name to render,
  the second is the file contents."
  [{:keys [name] :as data} opts]
  ;; e.g. if we have something like 'src/config/limo-driver/example.edn' then it will replaced with 'src/config/{{sanitized}}/example.edn'
  (letfn [(render-file [file]
            [(str/replace file "limo_driver" "{{sanitized}}")
             (render file data)])]
    (map render-file (files-to-render opts))))

(defn limo-driver
  "Quick navigate web site using Limo"
  [name & opts]
  (let [dash-opts (map (partial str "--") valid-options)
        plus-opts (map (partial str "+") valid-options)
        data      {:name      name
                   :sanitized (name-to-path name)}]

    ;; If we pass some ops then it must be valid
    (doseq [opt opts]
      (if (not (some #{opt} (concat dash-opts plus-opts)))
        (apply main/abort (str "Invalid option: ") opt ". Valid options are" plus-opts)))

    (main/info "Generating fresh 'lein new' limo-driver project.")
    (main/info "Please see README.md for more informtation.")
    (let [data  (template-data name opts)
          files (format-files-args data opts)]
      (apply ->files data files))

    ;; Perform the initial git commit
    (cjg/git-init name)
    (let [repo (cjg/load-repo name)]
      (cjg/git-add repo ".")
      (cjg/git-commit repo (str "lein new limo-driver " name " " (str/join " " opts))))))
