(ns keechma.next.helix.classified)

(defn get-element-props
  [props]
  (into {} (filter (fn [[k _]] (simple-keyword? k)) props)))

(defn get-classes [props classes & togglers]
  (flatten (concat [classes] (mapv (fn [toggler] (toggler props)) togglers))))
