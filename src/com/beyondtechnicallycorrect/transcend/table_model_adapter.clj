(ns com.beyondtechnicallycorrect.transcend.table-model-adapter
  (:use com.beyondtechnicallycorrect.transcend.model))

(defn create-table-model []
  (let [model (create-model)]
    (proxy [javax.swing.table.AbstractTableModel] []
      (getRowCount []
        java.lang.Short/MAX_VALUE)
      (getColumnClass
        [columnIndex]
        java.lang.Object)
      (getColumnCount []
        java.lang.Short/MAX_VALUE)
      (getColumnName
        [column]
        (if (== 0 column)
          ""
          (proxy-super getColumnName (dec column))))
      (getValueAt
        [row column]
        (if (== 0 column)
          (inc row)
          (get-displayed-value-at model row (inc column))))
      (isCellEditable
        [row column]
        (not= column 0))
      (setValueAt
        [value row column]
        (set-user-entered-value-at! model row (inc column) value)))))
