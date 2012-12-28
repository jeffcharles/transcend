(ns transcend.grid-test
  (:use clojure.test
       transcend.grid))

(deftest creation
  (is (= [] @(create-grid))))

(deftest get-from-empty
  (is (nil? (get-grid-value (create-grid) 0 0))))

(deftest set-first-val
  (let [grid (create-grid)]
    (set-grid-value! grid 0 0 :set)
    (is (= [[:set]] @grid))))

(deftest get-from-set
  (let [grid (create-grid)]
    (set-grid-value! grid 0 0 :set)
    (is (= :set (get-grid-value grid 0 0)))))

(deftest get-from-further-row
  (let [grid (create-grid)]
    (set-grid-value! grid 2 1 :set)
    (is (= :set (get-grid-value grid 2 1)))))

(deftest get-from-further-col
  (let [grid (create-grid)]
    (set-grid-value! grid 1 2 :set)
    (is (= :set (get-grid-value grid 1 2)))))
