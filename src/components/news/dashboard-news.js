import React from 'react';
import {
  Card,
  CardBody,
  CardTitle,
  Col,
  CardImg,
  CardText,
  Row,
} from 'reactstrap';

const DashboardNews = (props) => {
  return (
    <Row>
      <Col xs='3' lg='3' sm='3'>
        <Card>
          <CardImg
            variant='top'
            src='https://image.freepik.com/free-vector/health-professional-team-concept-illustration_114360-1608.jpg'
          />
          <CardBody>
            <CardTitle>Card Title</CardTitle>
            <CardText>
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </CardText>
          </CardBody>
        </Card>
      </Col>
      <Col xs='3' lg='3' sm='3'>
        <Card>
          <CardImg
            variant='top'
            src='https://image.freepik.com/free-vector/health-professional-team-concept-illustration_114360-1608.jpg'
          />
          <CardBody>
            <CardTitle>Card Title</CardTitle>
            <CardText>
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </CardText>
          </CardBody>
        </Card>
      </Col>
      <Col xs='3' lg='3' sm='3'>
        <Card>
          <CardImg
            variant='top'
            src='https://image.freepik.com/free-vector/health-professional-team-concept-illustration_114360-1608.jpg'
          />
          <CardBody>
            <CardTitle>Card Title</CardTitle>
            <CardText>
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </CardText>
          </CardBody>
        </Card>
      </Col>
      <Col xs='3' lg='3' sm='3'>
        <Card>
          <CardImg
            variant='top'
            src='https://image.freepik.com/free-vector/health-professional-team-concept-illustration_114360-1608.jpg'
          />
          <CardBody>
            <CardTitle>Card Title</CardTitle>
            <CardText>
              Some quick example text to build on the card title and make up the
              bulk of the card's content.
            </CardText>
          </CardBody>
        </Card>
      </Col>
    </Row>
  );
};

export default DashboardNews;
