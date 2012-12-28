(ns transcend.grid)

(declare extend-rows)
(declare extend-cols)
(declare get-row-count)
(declare row-exists?)
(declare get-col-count)
(declare col-exists?)

(defn create-grid
  []
  (atom []))

(defn get-grid-value
  [grid x y]
  (let [grid @grid]
    (if (and (row-exists? grid x) (col-exists? grid x y))
      (nth (nth grid x) y)
      nil)))

(defn set-grid-value!
  [grid x y value]
  (swap! grid (fn [grid]
    (let [grid (if (row-exists? grid x)
                 grid
                 (extend-rows grid (- (inc x) (get-row-count grid))))
          grid (if (col-exists? grid x y)
                 grid
                 (extend-cols grid x (- (inc y) (get-col-count grid x))))]
    (assoc grid x (assoc (nth grid x) y value))))))

(defn- get-extended-coll
  [coll num-to-add init-val]
  (vec (concat coll (take num-to-add (repeat init-val)))))

(defn- extend-rows
  [grid num-to-add]
  (get-extended-coll grid num-to-add []))

(defn- extend-cols
  [grid row num-to-add]
  (assoc grid row (get-extended-coll (nth grid row) num-to-add nil)))

(defn- get-row-count
  [grid]
  (count grid))

(defn- row-exists?
  [grid x]
  (>= (dec (get-row-count grid)) x))

(defn- get-col-count
  [grid x]
  (if-not (row-exists? grid x) 0 (count (nth grid x))))

(defn- col-exists?
  [grid x y]
  (>= (dec (get-col-count grid x)) y))
