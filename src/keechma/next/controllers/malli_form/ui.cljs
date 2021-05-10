(ns keechma.next.controllers.malli-form.ui
  (:require [keechma.next.controllers.malli-form :as mfc]
            [keechma.malli-forms.core :as mf]
            [keechma.next.helix.core :refer [use-meta-sub dispatch]]
            [helix.hooks :as hooks]))

(defn use-form [props controller]
  (use-meta-sub props controller mfc/get-form))

(defn use-get-in-data
  ([props controller attr] (use-get-in-data props controller attr identity))
  ([props controller attr processor]
   (let [get-in-data-cb (hooks/use-callback
                         [(pr-str attr)]
                         (fn [meta-state]
                           (when-let [form (mfc/get-form meta-state)]
                             (processor (mf/get-in-data form attr)))))]
     (use-meta-sub props controller get-in-data-cb))))

(defn use-get-in-errors [props controller attr]
  (let [get-in-errors-cb (hooks/use-callback
                          [(pr-str attr)]
                          (fn [meta-state]
                            (when-let [form (mfc/get-form meta-state)]
                              (mf/get-in-errors form attr))))]
    (mf/format-error-messages (use-meta-sub props controller get-in-errors-cb))))

(defn on-partial-change
  ([props controller attr value] (on-partial-change props controller attr value {}))
  ([props controller attr value opts]
   (dispatch props controller :keechma.form/on-partial-change (assoc opts :input/attr attr :input/value value))))

(defn on-atomic-change
  ([props controller attr value] (on-atomic-change props controller attr value {}))
  ([props controller attr value opts]
   (dispatch props controller :keechma.form/on-atomic-change (assoc opts :input/attr attr :input/value value))))

(defn on-commit-change
  ([props controller attr] (on-commit-change props controller attr {}))
  ([props controller attr opts]
   (dispatch props controller :keechma.form/on-commit-change (assoc opts :input/attr attr))))