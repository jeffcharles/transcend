(ns com.beyondtechnicallycorrect.transcend.grid)

(declare get-col-count)
(declare get-row-count)
(declare extend-rows)
(declare extend-cols)
(declare row-exists?)
(declare col-exists?)

(defn create-grid
  []
  (atom (vec (take 5 (repeat (vec (take 5 (repeat nil))))))))

(defn get-grid-value
  [grid row col]
  (let [grid @grid]
    (if (and (row-exists? grid row) (col-exists? grid row col))
      (nth (nth grid row) col)
      nil)))

(defn set-grid-value!
  [grid row col value]
  (swap! grid (fn [grid]
    (let [grid (if (row-exists? grid row)
                 grid
                 (extend-rows grid (- (inc row) (get-row-count grid))))
          grid (if (col-exists? grid row col)
                 grid
                 (extend-cols grid row (- (inc col) (get-col-count grid row))))]
    (assoc grid row (assoc (nth grid row) col value))))))

(defn- get-col-count
  [grid row]
  (if-not (row-exists? grid row) 0 (count (nth grid row))))

(defn- get-row-count
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
  [grid row]
  (>= (dec (get-row-count grid)) row))

(defn- col-exists?
  [grid row col]
  (>= (dec (get-col-count grid row)) col))
