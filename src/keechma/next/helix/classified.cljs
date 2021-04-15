(ns keechma.next.helix.classified
  (:require [clojure.string :as str])
  (:require-macros [keechma.next.helix.classified :refer [defclassified]]))

(defn get-element-props
  [props]
  (into {} (filter (fn [[k _]] (simple-keyword? k)) props)))

(defn get-classes [props classes & togglers]
  (flatten (concat [classes] (mapv (fn [toggler] (toggler props)) togglers))))