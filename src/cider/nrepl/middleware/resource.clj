(ns cider.nrepl.middleware.resource
  (:require [clojure.java.io :as io]
            [clojure.tools.nrepl.transport :as transport]
            [clojure.tools.nrepl.middleware :refer [set-descriptor!]]
            [clojure.tools.nrepl.misc :refer [response-for]]
            [cider.nrepl.middleware.util.misc :as u]))

(defn resource-path [name]
  (when-let [resource (io/resource name)]
    (.getPath resource)))

(defn resource-reply
  [{:keys [name transport] :as msg}]
  (try
    (transport/send
     transport
     (response-for msg
                   :resource-path (resource-path name)
                   :status :done))
    (catch Exception e
      (transport/send
       transport
       (response-for msg (u/err-info e :resource-error))))))

(defn wrap-resource
  "Middleware that provides the path to resource."
  [handler]
  (fn [{:keys [op] :as msg}]
    (if (= "resource" op)
      (resource-reply msg)
      (handler msg))))

(set-descriptor!
 #'wrap-resource
 {:handles
  {"resource"
   {:doc "Obtain the path to a resource."
    :requires {"name" "The name of the resource in question."}
    :returns {"resource-path" "The file path to a resource."}}}})
