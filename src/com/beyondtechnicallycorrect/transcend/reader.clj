(ns com.beyondtechnicallycorrect.transcend.reader
  (:require [com.beyondtechnicallycorrect.transcend.grid :as grid]))

(defn get-displayed-value-at
  [model row col]
  (:displayed-value (grid/get-grid-value model row col)))
