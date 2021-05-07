(ns keechma.next.controllers.entitydb
  (:require [keechma.entitydb.core :as edb]
            [keechma.next.controllers.entitydb.protocols :as pt]
            [keechma.next.protocols :as keechma-pt]
            [keechma.entitydb.query :as q]
            [keechma.next.controllers.entitydb.controller]))

(derive :keechma/entitydb :keechma/controller)

(def insert-entity edb/insert-entity)
(def insert-entities edb/insert-entities)
(def insert-named edb/insert-named)
(def insert-collection edb/insert-collection)
(def remove-entity edb/remove-entity)
(def remove-named edb/remove-named)
(def remove-collection edb/remove-collection)
(def get-entity edb/get-entity)
(def get-named edb/get-named)
(def get-collection edb/get-collection)
(def get-ident-for-named edb/get-ident-for-named)
(def get-idents-for-collection edb/get-idents-for-collection)
(def get-entity-from-ident edb/get-entity-from-ident)
(def get-entities-from-idents edb/get-entities-from-idents)

(def include q/include)
(def reverse-include q/reverse-include)
(def recur-on q/recur-on)
(def switch q/switch)

(def insert-entity! (keechma-pt/make-api-proxy pt/insert-entity!))
(def insert-entities! (keechma-pt/make-api-proxy pt/insert-entities!))
(def insert-named! (keechma-pt/make-api-proxy pt/insert-named!))
(def insert-collection! (keechma-pt/make-api-proxy pt/insert-collection!))
(def remove-entity! (keechma-pt/make-api-proxy pt/remove-entity!))
(def remove-named! (keechma-pt/make-api-proxy pt/remove-named!))
(def remove-collection! (keechma-pt/make-api-proxy pt/remove-collection!))
(defn update! [{:keechma/keys [app]} controller-name update-fn & args]
  (let [api* (keechma-pt/-get-api* app controller-name)]
    (pt/update! @api* update-fn args)))