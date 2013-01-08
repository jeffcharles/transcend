(ns transcend.model
  (:use transcend.grid))

(declare set-displayed-value-at!)
(declare is-formula?)

(defn create-model []
  (create-grid))

(defn get-user-entered-value-at
  [model row col]
  (:user-entered (get-grid-value model row col)))

(defn get-displayed-value-at
  [model row col]
  (:displayed-value (get-grid-value model row col)))

(defn set-user-entered-value-at!
  [model row col val]
  (let [cur-val (get-grid-value model row col)]
    (set-grid-value! model row col (assoc cur-val :user-entered val))
    (set-displayed-value-at! model row col
      (if (is-formula? val)
        (-> (read-string val) eval)
        val))))

(defn- set-displayed-value-at!
  [model row col val]
  (let [cur-val (get-grid-value model row col)]
    (set-grid-value! model row col (assoc cur-val :displayed-value val))))

(defn- is-formula?
  [val]
  (let [trimmed-val (clojure.string/trim val)]
    (.startsWith trimmed-val "(")))
