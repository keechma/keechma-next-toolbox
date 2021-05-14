(ns keechma.next.controllers.entitydb.ui
  (:require [helix.hooks :as hooks]
            [keechma.next.helix.core :refer [use-sub]]
            [keechma.entitydb.core :as edb]))

(defn use-memoized-deps [& deps]
  (let [deps' (into [] deps)
        ref* (hooks/use-ref deps')]
    (when (not= @ref* deps')
      (reset! ref* deps'))
    @ref*))

(defn use-entity-from-ident
  ([props edb-key ident]
   (use-entity-from-ident props edb-key ident nil))
  ([props edb-key ident include-query]
   (let [deps (use-memoized-deps ident include-query)
         getter (hooks/use-callback
                 deps
                 (fn [entitydb]
                   (when ident
                     (edb/get-entity-from-ident entitydb ident include-query))))]
     (use-sub props edb-key getter))))

(defn use-entities-from-idents
  ([props edb-key idents]
   (use-entities-from-idents props edb-key idents nil))
  ([props edb-key idents include-query]
   (let [deps (use-memoized-deps idents include-query)
         getter (hooks/use-callback
                 deps
                 (fn [entitydb]
                   (when (seq idents)
                     (edb/get-entities-from-idents entitydb idents include-query))))]
     (use-sub props edb-key getter))))
