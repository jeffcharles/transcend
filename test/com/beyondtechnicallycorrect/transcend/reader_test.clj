(ns com.beyondtechnicallycorrect.transcend.reader-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.reader
              :refer [get-displayed-range]]
            [com.beyondtechnicallycorrect.transcend.model
              :refer [create-model set-user-entered-value-at!]]))

(deftest get-model-range-basic
  (let [grid (create-model)]
    (set-user-entered-value-at! grid 1 1 "1")
    (set-user-entered-value-at! grid 2 1 "2")
    (set-user-entered-value-at! grid 3 1 "3")
    (set-user-entered-value-at! grid 1 2 "4")
    (set-user-entered-value-at! grid 2 2 "5")
    (set-user-entered-value-at! grid 3 2 "6")
    (is (= [[2 5] [3 6]] (get-displayed-range grid [2 1] [3 2])))))

(deftest get-model-range-with-funcs
  (let [grid (create-model)]
    (set-user-entered-value-at! grid 0 0 "2")
    (set-user-entered-value-at! grid 0 1 "3")
    (set-user-entered-value-at! grid 1 0 "(+ (A1) (B1))")
    (set-user-entered-value-at! grid 1 1 "(* (A1) (B1))")
    (is (= [[2 3] [5 6]] (get-displayed-range grid [0 0] [1 1])))))
