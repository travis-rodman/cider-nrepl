(def VERSION "0.9.0-SNAPSHOT")

(defproject cider/cider-nrepl VERSION
  :description "nREPL middlewares for CIDER"
  :url "https://github.com/clojure-emacs/cider-nrepl"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/tools.nrepl "0.2.10"]
                 [org.tcrawley/dynapath "0.2.3"]
                 [debugger "0.1.7"]
                 ^:source-dep [compliment "0.2.1-SNAPSHOT"]
                 ^:source-dep [cljs-tooling "0.1.5-SNAPSHOT"]
                 ^:source-dep [cljfmt "0.1.10"]
                 ^:source-dep [org.clojure/java.classpath "0.2.2"]
                 ^:source-dep [org.clojure/tools.namespace "0.2.10"]
                 ^:source-dep [org.clojure/tools.trace "0.7.8"]
                 ^:source-dep [org.clojure/tools.reader "0.9.1"]]
  :exclusions [org.clojure/clojure]
  :test-paths ["test/common"] ;; See `test-clj` and `test-cljs` profiles below.
  :plugins [[thomasa/mranderson "0.4.3"]]
  :filespecs [{:type :bytes :path "cider/cider-nrepl/project.clj" :bytes ~(slurp "project.clj")}]
  :profiles {:provided {:dependencies [[org.clojure/clojure "1.5.1"]]}

             :dev {:repl-options {:nrepl-middleware [cider.nrepl.middleware.apropos/wrap-apropos
                                                     cider.nrepl.middleware.classpath/wrap-classpath
                                                     cider.nrepl.middleware.complete/wrap-complete
                                                     cider.nrepl.middleware.debug/wrap-debug
                                                     cider.nrepl.middleware.format/wrap-format
                                                     cider.nrepl.middleware.info/wrap-info
                                                     cider.nrepl.middleware.inspect/wrap-inspect
                                                     cider.nrepl.middleware.macroexpand/wrap-macroexpand
                                                     cider.nrepl.middleware.ns/wrap-ns
                                                     cider.nrepl.middleware.pprint/wrap-pprint
                                                     cider.nrepl.middleware.resource/wrap-resource
                                                     cider.nrepl.middleware.stacktrace/wrap-stacktrace
                                                     cider.nrepl.middleware.test/wrap-test
                                                     cider.nrepl.middleware.trace/wrap-trace
                                                     cider.nrepl.middleware.undef/wrap-undef]}
                   ;; The "sources" jar should be the same version as Clojure,
                   ;; but bad sources jars were deployed to the Maven Central
                   ;; "releases" repo, so for the moment, use sources from
                   ;; "snapshot" builds to run tests.
                   ;; See http://dev.clojure.org/jira/browse/CLJ-1161.
                   :repositories [["snapshots"
                                   "https://oss.sonatype.org/content/repositories/snapshots"]]
                   :dependencies [[org.clojure/tools.nrepl "0.2.10"]
                                  [org.clojure/clojure "1.5.2-SNAPSHOT"
                                   :classifier "sources"]
                                  [org.clojure/clojure "1.5.1"
                                   :classifier "javadoc"]]}

             :test {:resource-paths ["test/resources"]}
             :test-clj {:test-paths ["test/clj"]}
             :test-cljs {:test-paths ["test/cljs"]
                         :dependencies [[com.cemerick/piggieback "0.2.0"]
                                        [org.clojure/clojurescript "0.0-3196"]]}

             :coveralls {:plugins [[lein-cloverage "1.0.2"]
                                   [lein-shell "0.4.0"]]
                         :aliases {"coveralls" ["do" "cloverage" "--coveralls,"
                                                "shell" "curl" "-F"
                                                "json_file=@target/coverage/coveralls.json"
                                                "https://coveralls.io/api/v1/jobs"]}}

             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0-beta1"]]}

             :cljfmt {:plugins [[lein-cljfmt "0.1.10"]]}
             :eastwood {:plugins [[jonase/eastwood "0.2.1"]]
                        :eastwood {:config-files ["eastwood.clj"]}}})
