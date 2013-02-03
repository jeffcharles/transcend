(ns com.beyondtechnicallycorrect.transcend.table-model-adapter
  (:require [com.beyondtechnicallycorrect.transcend.model :as model]
            [com.beyondtechnicallycorrect.transcend.editing-cell
              :refer [get-cell-being-edited]]))

(defn create-table-model
  []
  (let [model (model/create-model)]
    (proxy [javax.swing.table.AbstractTableModel] []
      (getRowCount
        []
        java.lang.Short/MAX_VALUE)
      (getColumnClass
        [columnIndex]
        java.lang.Object)
      (getColumnCount
        []
        java.lang.Short/MAX_VALUE)
      (getColumnName
        [column]
        (if (== 0 column)
          ""
          (proxy-super getColumnName (dec column))))
      (getValueAt
        [row column]
        (let [is-editing? (= (get-cell-being-edited) [row column])
              value-getter (if is-editing?
                             model/get-user-entered-value-at
                             model/get-displayed-value-at)]
          (if (== 0 column)
            (inc row)
            (value-getter model row (dec column)))))
      (isCellEditable
        [row column]
        (not= column 0))
      (setValueAt
        [value row column]
        (model/set-user-entered-value-at! model row (dec column) value)))))
