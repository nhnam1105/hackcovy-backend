import React from 'react';
import { Line } from 'react-chartjs-2';
import { Card, CardBody, Col, Row } from 'reactstrap';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import VietnamMap from '../../components/news/vietnam-map';
import TableCases from '../../components/news/table-cases';
import WorldReport from '../../components/news/world-report';
import DashboardNews from '../../components/news/dashboard-news';

// Card Chart 1
const cardChartData1 = (canvas) => {
  const ctx = canvas.getContext('2d');
  const gradient = ctx.createLinearGradient(0, 0, 0, 300);
  gradient.addColorStop(0, '#F0A1A1');
  gradient.addColorStop(0.3, '#FFF');
  gradient.addColorStop(1, '#FFF');
  return {
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
      {
        label: 'My First dataset',
        backgroundColor: gradient,
        borderColor: '#F96E64',
        borderWidth: '2',
        pointBackgroundColor: '#F96E64',
        pointBorderColor: '#F96E64',
        data: [1, 18, 9, 17, 34, 22, 11],
      },
    ],
  };
};

const cardChartOpts1 = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips,
  },
  maintainAspectRatio: false,
  legend: {
    display: false,
  },
  scales: {
    xAxes: [
      {
        gridLines: {
          color: 'transparent',
          zeroLineColor: 'transparent',
        },
        ticks: {
          fontSize: 2,
          fontColor: 'transparent',
        },
      },
    ],
    yAxes: [
      {
        display: false,
      },
    ],
  },
  elements: {
    line: {
      borderWidth: 1,
    },
    point: {
      radius: 4,
      hitRadius: 10,
      hoverRadius: 4,
    },
  },
};

// Card Chart 2
const cardChartData2 = (canvas) => {
  const ctx = canvas.getContext('2d');
  const gradient = ctx.createLinearGradient(0, 0, 0, 300);
  gradient.addColorStop(0, '#9EDDB9');
  gradient.addColorStop(0.3, '#FFF');
  gradient.addColorStop(1, '#FFF');
  return {
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
      {
        label: 'My First dataset',
        backgroundColor: gradient,
        borderColor: '#2DB064',
        borderWidth: '2',
        pointBackgroundColor: '#2DB064',
        pointBorderColor: '#2DB064',
        data: [1, 18, 9, 17, 34, 22, 11],
      },
    ],
  };
};

const cardChartOpts2 = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips,
  },
  maintainAspectRatio: false,
  legend: {
    display: false,
  },
  scales: {
    xAxes: [
      {
        gridLines: {
          color: 'transparent',
          zeroLineColor: 'transparent',
        },
        ticks: {
          fontSize: 2,
          fontColor: 'transparent',
        },
      },
    ],
    yAxes: [
      {
        display: false,
      },
    ],
  },
  elements: {
    line: {
      tension: 0.00001,
      borderWidth: 1,
    },
    point: {
      radius: 4,
      hitRadius: 10,
      hoverRadius: 4,
    },
  },
};

const Dashboard = (props) => {
  return (
    <div className='animated fadeIn'>
      <Row>
        <Col xs='12' sm='9' lg='9'>
          <Row>
            <Col xs='12' sm='6' lg='3'>
              <Card className=''>
                <CardBody className='pb-0'>
                  <div className='text-value'>9.823</div>
                  <div>Total cases</div>
                </CardBody>
                <div className='chart-wrapper mx-3' style={{ height: '70px' }}>
                  <Line
                    data={cardChartData1}
                    options={cardChartOpts1}
                    height={70}
                  />
                </div>
              </Card>
            </Col>

            <Col xs='12' sm='6' lg='3'>
              <Card className=''>
                <CardBody className='pb-0'>
                  <div className='text-value'>9.823</div>
                  <div>Recovered</div>
                </CardBody>
                <div className='chart-wrapper mx-3' style={{ height: '70px' }}>
                  <Line
                    data={cardChartData2}
                    options={cardChartOpts2}
                    height={70}
                  />
                </div>
              </Card>
            </Col>

            <Col xs='12' sm='6' lg='3'>
              <Card className=''>
                <CardBody className='pb-0'>
                  <div className='text-value'>9.823</div>
                  <div>Active case</div>
                </CardBody>
                <div className='chart-wrapper' style={{ height: '70px' }}>
                  <Line
                    data={cardChartData1}
                    options={cardChartOpts1}
                    height={70}
                  />
                </div>
              </Card>
            </Col>

            <Col xs='12' sm='6' lg='3'>
              <Card className=''>
                <CardBody className='pb-0'>
                  <div className='text-value'>9.823</div>
                  <div>Total death</div>
                </CardBody>
                <div className='chart-wrapper mx-3' style={{ height: '70px' }}>
                  <Line
                    data={cardChartData1}
                    options={cardChartOpts1}
                    height={70}
                  />
                </div>
              </Card>
            </Col>
          </Row>

          <Row>
            <Col>
              <Card>
                <CardBody>
                  <Row>
                    <Col xs='6' sm='6' lg='6'>
                      <VietnamMap />
                    </Col>
                    <Col xs='6' sm='6' lg='6'>
                      <TableCases />
                    </Col>
                  </Row>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </Col>

        <Col xs='12' sm='3' lg='3'>
          <WorldReport />
        </Col>
      </Row>
      <DashboardNews />
    </div>
  );
};

export default Dashboard;
