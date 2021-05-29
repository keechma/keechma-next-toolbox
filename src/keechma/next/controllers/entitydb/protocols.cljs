(ns keechma.next.controllers.entitydb.protocols)

(defprotocol IEntityDbApi
  (insert-entity! [_ entity-type data])
  (insert-entities! [_ entity-type entities])
  (insert-named! [_ entity-type entity-name data] [_ entity-type entity-name data n-meta])
  (insert-collection! [_ entity-type collection-name data] [_ entity-type collection-name data c-meta])
  (append-collection! [_ entity-type collection-name data] [_ entity-type collection-name data c-meta])
  (prepend-collection! [_ entity-type collection-name data] [_ entity-type collection-name data c-meta])
  (remove-entity! [_ entity-type id])
  (remove-named! [_ entity-name])
  (remove-collection! [_ collection-name])
  (update! [_ update-fn args]))