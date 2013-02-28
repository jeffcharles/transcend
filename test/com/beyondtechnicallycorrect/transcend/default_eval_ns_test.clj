(ns com.beyondtechnicallycorrect.transcend.default-eval-ns-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.default-eval-ns
              :refer [eval-transcend-fn get-grid-val get-col-num get-row-num]]
            [com.beyondtechnicallycorrect.transcend.model :as model]))

(declare eval-given-a1-letternum-should-transform-to-get-grid-value)

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
    (model/set-user-entered-value-at! grid 1 2 "5")
    (model/set-user-entered-value-at! grid 2 1 "(get-grid-value 1 2)")
    (is (= 5 (model/get-displayed-value-at grid 2 1)))))

(deftest col-num-when-a-should-ret-0
   (is (= 0 (get-col-num "A"))))

(deftest col-num-when-b-should-ret-1
   (is (= 1 (get-col-num "B"))))

(deftest col-num-when-c-should-ret-2
   (is (= 2 (get-col-num "C"))))

(deftest col-num-when-aa-should-ret-26
   (is (= 26 (get-col-num "AA"))))

(deftest col-num-when-ab-should-ret-27
   (is (= 27 (get-col-num "AB"))))

(deftest col-num-when-ba-should-ret-52
   (is (= 52 (get-col-num "BA"))))

(deftest row-num-when-valid-should-ret-correct-value
   (is (= 1 (get-row-num "2"))))

(deftest eval-letternum-should-transform-to-get-grid-value
  (eval-given-a1-letternum-should-transform-to-get-grid-value "(+ 1 (A1))"))

(deftest eval-letternum-with-lowercase-letter-should-transform-to-get-grid-value
  (eval-given-a1-letternum-should-transform-to-get-grid-value "(+ 1 (a1))"))

(defn- eval-given-a1-letternum-should-transform-to-get-grid-value
  [str-with-letternum]
  (let [grid (model/create-model)]
    (model/set-user-entered-value-at! grid 0 0 "1")
    (model/set-user-entered-value-at! grid 0 1 str-with-letternum)
    (is (= 2 (model/get-displayed-value-at grid 0 1)))))
