(ns keechma.next.controllers.malli-form
  (:require [keechma.malli-forms.core :as mf]
            [keechma.pipelines.core :as pp :refer-macros [pipeline!]]))

(def form-key :keechma.malli/form)

(defn init-form
  ([meta-state form] (init-form meta-state form {}))
  ([meta-state form initial-data]
   (assoc meta-state form-key (mf/reset form initial-data))))

(defn get-form [meta-state]
  (form-key meta-state))

(def on-partial-change
  "Use on-partial-change when collecting data from text inputs."
  (pipeline! [value _]
    (let [{input-attr :input/attr input-value :input/value} value]
      (pipeline! [value {:keys [meta-state*]}]
        (pp/swap! meta-state* update form-key mf/assoc-in-data input-attr input-value)
        (if (mf/valid-in? (form-key @meta-state*) input-attr input-value)
          (pp/swap! meta-state* update form-key mf/validate-optimistically-in input-attr)
          (pp/swap! meta-state* update form-key mf/validate-in input-attr))))))

(def on-atomic-change
  (pipeline! [value {:keys [meta-state*]}]
    (let [{input-attr :input/attr input-value :input/value} value]
      (pp/swap! meta-state* update form-key #(-> %
                                               (mf/assoc-in-data input-attr input-value)
                                               mf/validate)))))
(def on-commit-change
  (pipeline! [value {:keys [meta-state*]}]
    (pp/swap! meta-state* update form-key mf/validate)))

(defn wrap-submit [submit-pipeline]
  (pipeline! [value {:keys [meta-state*] :as ctrl}]
    (pp/swap! meta-state* update form-key mf/validate false)
    (if (-> @meta-state* form-key mf/valid?)
      (pipeline! [value {:keys [meta-state*]}]
        (-> @meta-state* form-key mf/get-coerced-data)
        submit-pipeline)
      (when ^boolean goog/DEBUG
        (js/console.warn "Trying to submit invalid form")
        (js/console.warn (with-out-str (cljs.pprint/pprint {:keechma/controller (:keechma.controller/name ctrl)
                                                            :data (-> @meta-state* form-key mf/get-data)
                                                            :errors (-> @meta-state* form-key (mf/get-errors false))})))))))

(def pipelines
  {:keechma.form/on-partial-change on-partial-change
   :keechma.form/on-atomic-change on-atomic-change
   :keechma.form/on-commit-change on-commit-change})



