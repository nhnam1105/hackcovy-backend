import React from 'react';
import { Table } from 'reactstrap';

const TableCases = (props) => {
  return (
    <Table hover responsive className='table-outline mb-0 d-none d-sm-table'>
      <thead className='thead-light'>
        <tr>
          <th className='text-center'>Tỉnh/Thành phố</th>
          <th>Tổng số ca</th>
          <th className='text-center'>Chữa khỏi</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td className='text-center'>
            <div>Hanoi</div>
          </td>
          <td>
            <div>192</div>
          </td>
          <td className='text-center'>33</td>
        </tr>
        <tr>
          <td className='text-center'>
            <div>Hanoi</div>
          </td>
          <td>
            <div>192</div>
          </td>
          <td className='text-center'>33</td>
        </tr>
        <tr>
          <td className='text-center'>
            <div>Hanoi</div>
          </td>
          <td>
            <div>192</div>
          </td>
          <td className='text-center'>33</td>
        </tr>
        <tr>
          <td className='text-center'>
            <div>Hanoi</div>
          </td>
          <td>
            <div>192</div>
          </td>
          <td className='text-center'>33</td>
        </tr>
        <tr>
          <td className='text-center'>
            <div>Hanoi</div>
          </td>
          <td>
            <div>192</div>
          </td>
          <td className='text-center'>33</td>
        </tr>
        <tr>
          <td className='text-center'>
            <div>Hanoi</div>
          </td>
          <td>
            <div>192</div>
          </td>
          <td className='text-center'>33</td>
        </tr>
      </tbody>
    </Table>
  );
};

export default TableCases;
