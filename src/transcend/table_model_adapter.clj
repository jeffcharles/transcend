(ns transcend.table-model-adapter
  (:use transcend.grid))

(defn create-table-model []
  (let [grid (create-grid)]
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
          (get-grid-value grid row (inc column))))
      (isCellEditable
        [row column]
        (not= column 0))
      (setValueAt
        [value row column]
        (set-grid-value! grid row (inc column) value)))))
