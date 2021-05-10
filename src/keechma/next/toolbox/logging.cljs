(ns keechma.next.toolbox.logging
  (:require [oops.core :refer [oget ocall oapply+ oget+ ocall+]]))

(def debug? ^boolean goog.DEBUG)

(defn make-group-logger [group-fn]
  (fn [& args]
    (when debug?
      (if (oget+ js/console group-fn)
        (oapply+ js/console group-fn (vec args))
        (oapply+ js/console :log (concat ["==>"] (vec args)))))))

(defn group-end []
  (when debug?
    (if (oget js/console :?groupEnd)
      (ocall js/console :groupEnd)
      (ocall js/console :log "<=="))))

(defn make-level-loger [level]
  (if debug?
    (let [level-fn (if (oget+ js/console level) level :log)]
      (fn [& args]
        (oapply+ js/console level (vec args))))
    (constantly nil)))

(def group (make-group-logger :?group))
(def group-collapsed (make-group-logger :?groupCollapsed))
(def log (make-level-loger :?log))
(def warn (make-level-loger :?warn))
(def error (make-level-loger :?error))
(def info (make-level-loger :?info))

(defn pp [& args]
  (when debug?
    (apply log (map (fn [a] (with-out-str (cljs.pprint/pprint a))) args))))
