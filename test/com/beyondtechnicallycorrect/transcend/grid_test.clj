(ns com.beyondtechnicallycorrect.transcend.grid-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.grid
              :refer [create-grid get-grid-value set-grid-value!]]))

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

