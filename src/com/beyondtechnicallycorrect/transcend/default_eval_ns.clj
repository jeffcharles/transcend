(ns com.beyondtechnicallycorrect.transcend.default-eval-ns
  (:require [com.beyondtechnicallycorrect.transcend.reader :as reader]
            [clojure.string :as string]))

(declare replace-cell-refs)

(defn get-grid-val
  [grid row col]
  (reader/get-displayed-value-at grid row col))

(defn get-col-num
  "Takes a column descriptor (one or more English letters) from the grid
  displayed to the user and transforms it into the column number which the
  underlying data model uses"
  [provided-col-descriptor]
  (let [upper-a-ascii-val 65
        letter-to-index #(- (int %) upper-a-ascii-val)
        num-letters-in-alphabet 26
        left-shift #(* num-letters-in-alphabet (inc %))]
    (->> (string/upper-case provided-col-descriptor)
      (map letter-to-index)
      (reduce #(+ (left-shift %) %2)))))

(defn get-row-num
  "Takes a row number from the grid displayed to the user and transforms it into
  the row number which the underlying data model uses"
  [provided-row-num]
  (dec (read-string provided-row-num)))

(declare ^:dynamic get-grid-value) ; Want to bind this name in the eval context
(defn eval-transcend-fn
  [grid transcend-fn]
  (try
    (binding [*ns*
      (find-ns 'com.beyondtechnicallycorrect.transcend.default-eval-ns)
              ; Partially apply to get simple access to grid state
              get-grid-value #(get-grid-val grid % %2)]
        (-> (read-string transcend-fn)
          (replace-cell-refs)
          (eval)))
    (catch RuntimeException e
      "#ERROR")))

(defn- replace-cell-refs
  "Replace all references to cell addresses (e.g., (A1)) with a call to
  get-grid-value."
  [form]
  (if (= (count form) 1)
    (let [first-elem (first form)
          cell-ref-matches
            (re-matches #"(?i)^([A-Z]+)([0-9]+)$" (name first-elem))
          is-cell-ref?
            (and (symbol? first-elem) cell-ref-matches)
          col-str (get cell-ref-matches 1)
          row-str (get cell-ref-matches 2)]
      (if is-cell-ref?
        (get-grid-value (get-row-num row-str) (get-col-num col-str))
        form))
    (cons
      (first form)
      (map #(if (list? %) (replace-cell-refs %) %) (rest form)))))
