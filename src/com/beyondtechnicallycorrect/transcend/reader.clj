(ns com.beyondtechnicallycorrect.transcend.reader
  (:require [com.beyondtechnicallycorrect.transcend.grid :as grid]))

(def ^:private displayed-val :displayed-value)

(defn get-displayed-value-at
  [model row col]
  (displayed-val (grid/get-grid-value model row col)))

(defn get-displayed-range
  [model [row1 col1] [row2 col2]]
  (map
    #(map displayed-val %)
    (grid/get-grid-range model [row1 col1] [row2 col2])))
