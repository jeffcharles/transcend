(ns transcend.grid)

(declare get-col-count-w-row)
(declare get-row-count-internal)
(declare extend-rows)
(declare extend-cols)
(declare row-exists?)
(declare col-exists?)

(defn create-grid
  []
  (atom (vec (take 5 (repeat (vec (take 5 (repeat nil))))))))

(defn get-grid-value
  [grid x y]
  (let [grid @grid]
    (if (and (row-exists? grid x) (col-exists? grid x y))
      (nth (nth grid x) y)
      nil)))

(defn get-row-count
  [grid]
  (get-row-count-internal @grid))

(defn get-col-count
  [grid]
  (let [grid @grid
        row-nums (range (get-row-count-internal grid))
        col-counts (map #(get-col-count-w-row grid %) row-nums)]
    (apply max col-counts)))

(defn set-grid-value!
  [grid x y value]
  (swap! grid (fn [grid]
    (let [grid (if (row-exists? grid x)
                 grid
                 (extend-rows grid (- (inc x) (get-row-count-internal grid))))
          grid (if (col-exists? grid x y)
                 grid
                 (extend-cols grid x (- (inc y) (get-col-count-w-row grid x))))]
    (assoc grid x (assoc (nth grid x) y value))))))

(defn- get-col-count-w-row
  [grid x]
  (if-not (row-exists? grid x) 0 (count (nth grid x))))

(defn- get-row-count-internal
  [grid]
  (count grid))

(defn- get-extended-coll
  [coll num-to-add init-val]
  (vec (concat coll (take num-to-add (repeat init-val)))))

(defn- extend-rows
  [grid num-to-add]
  (get-extended-coll grid num-to-add []))

(defn- extend-cols
  [grid row num-to-add]
  (assoc grid row (get-extended-coll (nth grid row) num-to-add nil)))

(defn- row-exists?
  [grid x]
  (>= (dec (get-row-count-internal grid)) x))

(defn- col-exists?
  [grid x y]
  (>= (dec (get-col-count-w-row grid x)) y))
