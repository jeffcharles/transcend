(ns com.beyondtechnicallycorrect.transcend.editing-cell-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.editing-cell :refer
    [get-cell-being-edited set-cell-being-edited! set-no-cell-being-edited!]]))

(deftest get-cell-being-edited-intial-state-should-be-nil
  (is (= (get-cell-being-edited) [nil nil])))

(deftest set-cell-being-edited
  (set-cell-being-edited! 1 2)
  (is (= (get-cell-being-edited) [1 2])))

(deftest set-no-cell-being-edited
  (set-cell-being-edited! 1 2)
  (set-no-cell-being-edited!)
  (is (= (get-cell-being-edited) [nil nil])))
