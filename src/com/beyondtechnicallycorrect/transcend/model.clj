(ns com.beyondtechnicallycorrect.transcend.model
  (:require [com.beyondtechnicallycorrect.transcend.grid :as grid]))

(declare set-displayed-value-at!)
(declare is-formula?)

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
      (if (is-formula? val)
        (-> (read-string val) eval)
        val))))

(defn- set-displayed-value-at!
  [model row col val]
  (let [cur-val (grid/get-grid-value model row col)]
    (grid/set-grid-value! model row col (assoc cur-val :displayed-value val))))

(defn- is-formula?
  [val]
  (let [trimmed-val (clojure.string/trim val)]
    (.startsWith trimmed-val "(")))
