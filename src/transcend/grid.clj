(ns transcend.grid)

(declare extend-rows!)
(declare extend-cols!)
(declare get-row-count)
(declare row-exists?)
(declare get-col-count)
(declare col-exists?)

(defn create-grid
  []
  (atom []))

(defn get-grid-value
  [grid x y]
  (if (and (row-exists? grid x) (col-exists? grid x y))
    (nth (nth @grid x) y)
    nil))

(defn set-grid-value!
  [grid x y value]
  (when-not (row-exists? grid x)
    (extend-rows! grid (- (inc x) (get-row-count grid))))
  (when-not (col-exists? grid x y)
    (extend-cols! grid x (- (inc y) (get-col-count grid x))))
  (swap! grid #(assoc % x (assoc (nth % x) y value))))

(defn- get-extended-coll
  [coll num-to-add init-val]
  (vec (concat coll (take num-to-add (repeat init-val)))))

(defn- extend-rows!
  [grid num-to-add]
  (swap! grid #(get-extended-coll % num-to-add [])))

(defn- extend-cols!
  [grid row num-to-add]
  (swap! grid #(assoc % row (get-extended-coll (nth % row) num-to-add nil))))

(defn- get-row-count
  [grid]
  (count @grid))

(defn- row-exists?
  [grid x]
  (>= (dec (get-row-count grid)) x))

(defn- get-col-count
  [grid x]
  (if-not (row-exists? grid x) 0 (count (nth @grid x))))

(defn- col-exists?
  [grid x y]
  (>= (dec (get-col-count grid x)) y))
