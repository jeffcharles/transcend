(ns transcend.table-model-adapter
  (:use transcend.grid))

(defn create-table-model []
  (let [grid (create-grid)]
    (proxy [javax.swing.table.AbstractTableModel] []
      (getRowCount []
        (get-row-count grid))
      (getColumnClass
        [columnIndex]
        java.lang.Object)
      (getColumnCount []
        (get-col-count grid))
      (getValueAt
        [row column]
        (if (== 0 column)
          (inc row)
          (get-grid-value grid row (inc column))))
      (isCellEditable
        [row column]
        (not= column 0)))))
