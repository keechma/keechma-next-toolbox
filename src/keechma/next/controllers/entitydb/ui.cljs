(ns keechma.next.controllers.entitydb.ui
  (:require [helix.hooks :as hooks]
            [keechma.next.helix.core :refer [use-sub]]
            [keechma.entitydb.core :as edb]))

(defn use-entity-from-ident
  ([props edb-key ident]
   (use-entity-from-ident props edb-key ident nil))
  ([props edb-key ident include-query]
   (let [getter (hooks/use-callback
                 [(pr-str ident) (pr-str include-query)]
                 (fn [entitydb]
                   (edb/get-entity-from-ident entitydb ident include-query)))]
     (use-sub props edb-key getter))))

(defn use-entities-from-idents
  ([props edb-key ident]
   (use-entities-from-idents props edb-key ident nil))
  ([props edb-key ident include-query]
   (let [getter (hooks/use-callback
                 [(pr-str ident) (pr-str include-query)]
                 (fn [entitydb]
                   (edb/get-entities-from-idents entitydb ident include-query)))]
     (use-sub props edb-key getter))))