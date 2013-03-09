(ns com.beyondtechnicallycorrect.transcend.reader
  (:require [com.beyondtechnicallycorrect.transcend.grid :as grid]))

(defn get-displayed-value-at
  [model row col]
  (:displayed-value (grid/get-grid-value model row col)))

(defn get-displayed-range
  [model [row1 col1] [row2 col2]]
  (map
    #(map :displayed-value %)
    (grid/get-grid-range model [row1 col1] [row2 col2])))
