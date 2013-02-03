(ns com.beyondtechnicallycorrect.transcend.default-eval-ns-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.default-eval-ns
              :refer [eval-transcend-fn get-grid-val]]
            [com.beyondtechnicallycorrect.transcend.model :as model]))

(deftest eval-valid-fn
  (is (= 5 (eval-transcend-fn nil "(+ 2 3)"))))

(deftest eval-unparseable-fn
  (is (= "#ERROR" (eval-transcend-fn nil "(+ 2 3"))))

(deftest evaling-in-own-ns
  (is (=
    "com.beyondtechnicallycorrect.transcend.default-eval-ns"
    (eval-transcend-fn nil "(str *ns*)"))))

(deftest get-grid-val-should-return-value
  (let [grid (model/create-model)]
    (model/set-user-entered-value-at! grid 1 2 5)
    (is (= 5 (get-grid-val grid 1 2)))))

(deftest get-grid-value-inside-evaled-fn-should-return-value
  (let [grid (model/create-model)]
    (model/set-user-entered-value-at! grid 1 2 5)
    (model/set-user-entered-value-at! grid 2 1 "(get-grid-value 1 2)")
    (is (= 5 (model/get-displayed-value-at grid 2 1)))))
