(ns com.beyondtechnicallycorrect.transcend.grid-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.grid
              :refer [create-grid get-grid-value get-grid-range set-grid-value!]]))

(deftest creation
  (is (= [[nil nil nil nil nil]
          [nil nil nil nil nil]
          [nil nil nil nil nil]
          [nil nil nil nil nil]
          [nil nil nil nil nil]] @(create-grid))))

(deftest get-from-empty
  (is (nil? (get-grid-value (create-grid) 0 0))))

(defn- get-from-set
  [x y]
  (let [grid (create-grid)]
    (set-grid-value! grid x y :set)
    (is (= :set (get-grid-value grid x y)))))

(deftest get-from-set-00 (get-from-set 0 0))
(deftest get-from-set-further-row (get-from-set 2 1))
(deftest get-from-set-further-col (get-from-set 1 2))

(deftest get-grid-range-valid
  (let [grid (create-grid)]
    (set-grid-value! grid 1 1 1)
    (set-grid-value! grid 2 1 2)
    (set-grid-value! grid 3 1 3)
    (set-grid-value! grid 1 2 4)
    (set-grid-value! grid 2 2 5)
    (set-grid-value! grid 3 2 6)
    (is
      (=
         [[2 5] [3 6]]
         (get-grid-range grid [2 1] [3 2])))))
