(ns com.beyondtechnicallycorrect.transcend.default-eval-ns
  (:require [com.beyondtechnicallycorrect.transcend.reader :as reader]))

(defn get-grid-val
  [grid row col]
  (reader/get-displayed-value-at grid row col))

(declare ^:dynamic get-grid-value) ; Want to bind this name in the eval context
(defn eval-transcend-fn
  [grid transcend-fn]
  (try
    (binding [*ns*
      (find-ns 'com.beyondtechnicallycorrect.transcend.default-eval-ns)
              ; Partially apply to get simple access to grid state
              get-grid-value #(get-grid-val grid % %2)]
        (load-string transcend-fn))
    (catch RuntimeException e
      "#ERROR")))
