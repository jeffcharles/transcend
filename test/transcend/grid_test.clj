(ns transcend.grid-test
  (:use clojure.test
       transcend.grid))

(deftest creation
  (is (= [[nil]] @(create-grid))))

(deftest get-from-empty
  (is (nil? (get-grid-value (create-grid) 0 0))))

(deftest set-first-val
  (let [grid (create-grid)]
    (set-grid-value! grid 0 0 :set)
    (is (= [[:set]] @grid))))

(defn- get-from-set
  [x y]
  (let [grid (create-grid)]
    (set-grid-value! grid x y :set)
    (is (= :set (get-grid-value grid x y)))))

(deftest get-from-set-00 (get-from-set 0 0))
(deftest get-from-set-further-row (get-from-set 2 1))
(deftest get-from-set-further-col (get-from-set 1 2))

