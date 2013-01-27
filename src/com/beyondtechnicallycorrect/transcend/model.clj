(ns com.beyondtechnicallycorrect.transcend.model
  (:require [com.beyondtechnicallycorrect.transcend.grid :as grid]
            [com.beyondtechnicallycorrect.transcend.default-eval-ns
              :as default-eval-ns]))

(declare set-displayed-value-at!)
(declare is-formula?)
(declare is-commented-formula?)

(defn create-model
  []
  (grid/create-grid))

(defn get-user-entered-value-at
  [model row col]
  (:user-entered (grid/get-grid-value model row col)))

(defn get-displayed-value-at
  [model row col]
  (:displayed-value (grid/get-grid-value model row col)))

(defn set-user-entered-value-at!
  [model row col val]
  (let [cur-val (grid/get-grid-value model row col)]
    (grid/set-grid-value! model row col (assoc cur-val :user-entered val))
    (set-displayed-value-at! model row col
      (cond (is-formula? val) (default-eval-ns/eval-transcend-fn val)
            (is-commented-formula? val) (.substring val 1)
            :else val))))

(defn- set-displayed-value-at!
  [model row col val]
  (let [cur-val (grid/get-grid-value model row col)]
    (grid/set-grid-value! model row col (assoc cur-val :displayed-value val))))

(defn- starts-with?
  [val starting-str]
  (let [trimmed-val (clojure.string/trim val)]
    (.startsWith trimmed-val starting-str)))

(defn- is-formula?
  [val]
  (starts-with? val "("))

(defn- is-commented-formula?
  [val]
  (and (starts-with? val "'") (is-formula? (.substring val 1))))
