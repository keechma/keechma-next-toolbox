(ns keechma.next.toolbox.event
  (:require [keechma.next.controller :as ctrl]))

(defprotocol IEvent
  (trigger [this ctrl]))

(defrecord DispatchEvent [controller-name event payload]
  IEvent
  (trigger [_ ctrl]
    (ctrl/dispatch ctrl controller-name event payload)))

(defrecord BroadcastEvent [event payload]
  IEvent
  (trigger [_ ctrl]
    (ctrl/broadcast ctrl event payload)))

(defn to-dispatch
  ([controller-name event]
   (to-dispatch controller-name event nil))
  ([controller-name event payload]
   (->DispatchEvent controller-name event payload)))

(defn to-broadcast
  ([event] (to-broadcast event nil))
  ([event payload]
   (->BroadcastEvent event payload)))